package io.microlam.aws.lambda.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;


//See https://www.theguild.nl/enriching-requests-with-an-aws-lambda-authorizer/

@JsonDeserialize(builder = AuthorizerResponse.Builder.class)
public class AuthorizerResponse {

	@JsonProperty("principalId")
    private String principalId;

	@JsonProperty("policyDocument")
    private PolicyDocument policyDocument;

	@JsonProperty("context")
    private Object context;

    private AuthorizerResponse(Builder builder) {
        this.principalId = builder.principalId;
        this.policyDocument = builder.policyDocument;
        this.context = builder.context;
    }

    public String getPrincipalId() {
        return principalId;
    }

    public PolicyDocument getPolicyDocument() {
        return policyDocument;
    }

    public Object getContext() {
        return context;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String principalId;
        private PolicyDocument policyDocument;
        private Object context;

        private Builder() { }

        public Builder principalId(String principalId) {
            this.principalId = principalId;
            return this;
        }

        public Builder policyDocument(PolicyDocument policyDocument) {
            this.policyDocument = policyDocument;
            return this;
        }

        public Builder context(Object context) {
            this.context = context;
            return this;
        }

        public AuthorizerResponse build() {
            return new AuthorizerResponse(this);
        }
    }
}