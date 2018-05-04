package eu.quinns.qlocation.aws;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import eu.quinns.qlocation.DeviceLocation;

import java.util.UUID;

public class LocationPersisterFunction implements RequestHandler<DeviceLocation, String>{


    /**
     * Handles a Lambda Function request
     *
     * @param input   The Lambda Function input
     * @param context The Lambda execution environment context object.
     * @return The Lambda Function output
     */

    public String handleRequest(DeviceLocation input, Context context) {
        LambdaLogger logger = context.getLogger();
        String tableName;
        if (System.getenv().containsKey("tableName")) {
            tableName = System.getenv("tableName");
            logger.log("Using env variable for table name: " + tableName);
        } else {
            tableName = "DeviceLocation2";
            logger.log("Using Default table name");
        }

        DeviceLocation location = input;

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable(tableName);
        System.out.printf("location is :" + location.toString());
        logger.log("location is : " + location.toString());

        final Item item = new Item()
                .withPrimaryKey("device_id", location.getDeviceId())
                .withDouble("latitude", location.getLatitude())
                .withDouble("longitude", location.getLongitude())
                .withLong("timestamp", location.getTimestamp());
        System.out.printf("Item is :" + item.toString());

        PutItemOutcome outcome = table.putItem(item);

        logger.log("Outcome is: " + outcome.getPutItemResult().toString());
        return "OK" + location.toString();
    }

  }
