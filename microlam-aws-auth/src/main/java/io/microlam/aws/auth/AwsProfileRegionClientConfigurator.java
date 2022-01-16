package io.microlam.aws.auth;



import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.awscore.client.builder.AwsClientBuilder;
import software.amazon.awssdk.regions.Region;


public class AwsProfileRegionClientConfigurator {
	
	protected static AwsProfileRegionClientConfigurator instance = new AwsProfileRegionClientConfigurator();
	protected static String profile = null;
	protected static Region region = Region.EU_WEST_1;

	public static AwsProfileRegionClientConfigurator getInstance() {
		return instance; 
	}	
	
	public static String getProfile() {
		return profile;
	}

	public static void setProfile(String profile) {
		AwsProfileRegionClientConfigurator.profile = profile;
	}

	public static Region getRegion() {
		return region;
	}

	public static void setRegion(Region region) {
		AwsProfileRegionClientConfigurator.region = region;
	}

	public <BuilderT extends AwsClientBuilder<BuilderT, ClientT>, ClientT> AwsClientBuilder<BuilderT, ClientT> configure(AwsClientBuilder<BuilderT, ClientT> builder) {
		builder.region(region);
		if (profile != null) {
			builder.credentialsProvider(ProfileCredentialsProvider.create(profile));
		}
		return builder;
	}
	
}
