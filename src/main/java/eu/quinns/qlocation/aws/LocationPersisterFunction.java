package eu.quinns.qlocation.aws;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import eu.quinns.qlocation.DeviceLocation;

import java.util.UUID;

public class LocationPersisterFunction implements com.amazonaws.services.lambda.runtime.RequestHandler{
    /**
     * Handles a Lambda Function request
     *
     * @param input   The Lambda Function input
     * @param context The Lambda execution environment context object.
     * @return The Lambda Function output
     */
    @Override
    public Object handleRequest(Object input, Context context) {
        if (! (input instanceof DeviceLocation)) {
            throw new UnsupportedOperationException("Bad object passed");
        }

        DeviceLocation location = (DeviceLocation) input;

        //final AmazonDynamoDBClient client = new AmazonDynamoDBClient(new EnvironmentVariableCredentialsProvider());
        //client.withRegion(Regions.US_WEST_2); // specify the region you created the table in.

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable("DeviceLocation");
        final Item item = new Item()
                .withPrimaryKey("id", UUID.randomUUID().toString()) // Every item gets a unique id
                .withString("deviceId", location.getDeviceId())
                .withDouble("lat", location.getLatitude())
                .withDouble("lng", location.getLongitude());
        table.putItem(item);
        return null;    }
}
