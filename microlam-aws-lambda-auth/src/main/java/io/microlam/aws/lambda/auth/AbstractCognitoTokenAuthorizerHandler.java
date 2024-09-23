package io.microlam.aws.lambda.auth;

import java.util.Collections;
import java.util.Map;

import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwk.HttpsJwks;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.ErrorCodes;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.resolvers.HttpsJwksVerificationKeyResolver;
import org.jose4j.keys.resolvers.JwksVerificationKeyResolver;
import org.jose4j.keys.resolvers.VerificationKeyResolver;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;


public abstract class AbstractCognitoTokenAuthorizerHandler implements RequestHandler<TokenAuthorizerContext, AuthorizerResponse> {

	private static Logger LOGGER = LoggerFactory.getLogger(AbstractCognitoTokenAuthorizerHandler.class);
	
	public static String iss;
	public static String aud;
	public static JwtConsumer jwtConsumer;

	/**
	 * @param iss The Cognito issuer (iss) claim should match your user pool like https://cognito-idp.{region}.amazonaws.com/{userpoolID}
	 * @param aud The Cognito audience (aud) claim should match the app client ID created in the Amazon Cognito user pool. 
	 */
	public AbstractCognitoTokenAuthorizerHandler(String iss, String aud, boolean requireAudienceClaim) {
		this(iss, aud, requireAudienceClaim, generateFromIssuer(iss));
	}

	public AbstractCognitoTokenAuthorizerHandler(String iss, String aud) {
		this(iss, aud, true, generateFromIssuer(iss));
	}

	protected static HttpsJwksVerificationKeyResolver generateFromIssuer(String iss) {
	    // The HttpsJwks retrieves and caches keys from a the given HTTPS JWKS endpoint.
	    // Because it retains the JWKs after fetching them, it can and should be reused
	    // to improve efficiency by reducing the number of outbound calls the the endpoint.
	    HttpsJwks httpsJkws = new HttpsJwks(iss + "/.well-known/jwks.json");

	    // The HttpsJwksVerificationKeyResolver uses JWKs obtained from the HttpsJwks and will select the
	    // most appropriate one to use for verification based on the Key ID and other factors provided
	    // in the header of the JWS/JWT.
	    return new HttpsJwksVerificationKeyResolver(httpsJkws);
	}
	
	/**
	 * @param iss The Cognito issuer (iss) claim should match your user pool like https://cognito-idp.{region}.amazonaws.com/{userpoolID}
	 * @param aud The Cognito audience (aud) claim should match the app client ID created in the Amazon Cognito user pool. 
	 * @param jsonWebKeySet For Cognito is downloadable from https://cognito-idp.{region}.amazonaws.com/{userPoolId}/.well-known/jwks.json
	 * @param requireAudienceClaim parameter can be used to indicate whether or not the presence of the audience claim is required.
	 */
	public AbstractCognitoTokenAuthorizerHandler(String iss, String aud, String jsonWebKeySet, boolean requireAudienceClaim) {
		this(iss, aud, requireAudienceClaim, generateFromKeySet(jsonWebKeySet));
	}

	public AbstractCognitoTokenAuthorizerHandler(String iss, String aud, String jsonWebKeySet) {
		this(iss, aud, true, generateFromKeySet(jsonWebKeySet));
	}

	protected static JwksVerificationKeyResolver generateFromKeySet(String jsonWebKeySet) {
		try {
			JsonWebKeySet jsonWebKeySet1;
			jsonWebKeySet1 = new JsonWebKeySet(jsonWebKeySet);
			JwksVerificationKeyResolver jwksResolver = new JwksVerificationKeyResolver(jsonWebKeySet1.getJsonWebKeys());
			return jwksResolver;
		}
		catch (JoseException e) {
			e.printStackTrace();
			throw new RuntimeException("Cannot generate JwksVerificationKeyResolver from KeySet [" + jsonWebKeySet + "]", e);
		}
	}
		
	public AbstractCognitoTokenAuthorizerHandler(String iss, String aud, boolean requireAudienceClaim, VerificationKeyResolver jwksResolver) {
    	this(iss, aud, new JwtConsumerBuilder()
                .setRequireExpirationTime() // the JWT must have an expiration time
                .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
                .setRequireSubject() // the JWT must have a subject claim
                .setExpectedIssuer(iss) // whom the JWT needs to have been issued by
                .setExpectedAudience(requireAudienceClaim, aud) // to whom the JWT is intended for
                .setVerificationKeyResolver(jwksResolver) // verify the signature with the public key
                .setJwsAlgorithmConstraints( // only allow the expected signature algorithm(s) in the given context
                        ConstraintType.PERMIT, AlgorithmIdentifiers.RSA_USING_SHA256) // which is only RS256 here
                .build()); // create the JwtConsumer instance
	}

	public AbstractCognitoTokenAuthorizerHandler(String iss, String aud, JwtConsumer jwtConsumer) {
	    AbstractCognitoTokenAuthorizerHandler.iss = iss;
		AbstractCognitoTokenAuthorizerHandler.aud = aud;
	    AbstractCognitoTokenAuthorizerHandler.jwtConsumer = jwtConsumer;
	}
	
    public AuthorizerResponse handleRequest(TokenAuthorizerContext input, Context context) {
    	//see https://docs.aws.amazon.com/cognito/latest/developerguide/amazon-cognito-user-pools-using-tokens-verifying-a-jwt.html
    	String jwt = input.getAuthorizationToken();
        try
        {
            //  Validate the JWT and process it to the Claims
            JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
            return generateResponse(jwtClaims, input, context);
        }
        catch (InvalidJwtException e)
        {
            // InvalidJwtException will be thrown, if the JWT failed processing or validation in anyway.
            // Hopefully with meaningful explanations(s) about what went wrong.
        	LOGGER.warn("Invalid JWT!",e);

            // Programmatic access to (some) specific reasons for JWT invalidity is also possible
            // should you want different error handling behavior for certain conditions.

            // Whether or not the JWT has expired being one common reason for invalidity
            if (e.hasExpired())
            {
                try {
                	LOGGER.warn("JWT expired at " + e.getJwtContext().getJwtClaims().getExpirationTime());
				}
				catch (MalformedClaimException e1) {
					LOGGER.warn("MalformedClaimException", e1);
				}
            }

            // Or maybe the audience was invalid
            if (e.hasErrorCode(ErrorCodes.AUDIENCE_INVALID))
            {
                try {
                	LOGGER.warn("JWT had wrong audience: " + e.getJwtContext().getJwtClaims().getAudience());
				}
				catch (MalformedClaimException e1) {
					LOGGER.warn("MalformedClaimException", e1);
				}
            }

            // Or maybe the issuer was invalid
            if (e.hasErrorCode(ErrorCodes.ISSUER_INVALID))
            {
                try {
                	LOGGER.warn("JWT had wrong issuer: " + e.getJwtContext().getJwtClaims().getIssuer());
				}
				catch (MalformedClaimException e1) {
					LOGGER.warn("MalformedClaimException", e1);
				}
            }

            throw new RuntimeException("Unauthorized");
        }
        
    }

	protected AuthorizerResponse generateResponse(JwtClaims jwtClaims, TokenAuthorizerContext input, Context context) {
		context.getLogger().log(jwtClaims.toString());
		
		MethodArn methodArn = new MethodArn(input.getMethodArn());
		
		String arn = methodArn.allowEverythingOnStage();
				
//		JsonBuilder2 jsonBuilder2 = new JsonBuilder2();
//		JsonElement jsonContext = jsonBuilder2.convert(jwtClaims.getClaimsMap());
		String sub;
		try {
		  sub = jwtClaims.getSubject();
		}
		catch (MalformedClaimException e) {
			throw new RuntimeException("", e);
		}
		
		//The context will be available in context.authorizer
		//As explained in https://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-lambda-authorizer-output.html
		Map<String, Object> authContext = jwtClaims.getClaimsMap();//jsonBuilder2.flattenAsMap(jsonContext.toString());
		
		Statement statement = Statement.builder()
				.effect("Allow")
	            .resource(arn)
	            .build();

	        PolicyDocument policyDocument = PolicyDocument.builder()
	            .statements(
	                Collections.singletonList(statement)
	            ).build();

		return AuthorizerResponse.builder()
	            .principalId(sub)
	    	    .policyDocument(policyDocument)
	            .context(authContext)
	            .build();
	}
	
}
