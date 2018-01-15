package com.mycompany.app;
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions

import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;


/**
 * Hello world!
 *
 */
public class Beta 
{
    public static void main( String[] args )
    {
        //def mickey = [
        //  "curl",  
        //  "${TEST_STACK_IP}:8080/green/timer/status"]
        // .execute().text
        def ARBITRARY_SUCCESS_PCT = 0.95
        def NUM_GREEN_DOTS = 100
        def GREEN_DOT_STATUS_DONE = '4'

        //Groovy notes:
        //fails: 
        // if (mickey.indexOf(
        //   GREEN_DOT_STATUS_DONE.multiply(
        //     ARBITRARY_SUCCESS_PCT*NUM_GREEN_DOTS)) < 0) { }
        //succeeds: 
        // if (mickey.indexOf('4'.multiply(95)) < 0) { }

        //if (mickey.indexOf(
        //  '4444444444444444444444444444444444444444444444444444444444') < 0) {
    	  //error "red rover3 ${TEST_STACK_IP}:8080/green/timer/status $mickey" 
        //}

        def DEFAULT_PAUSE_FOR_USERDATA_COMPLETION = 30*60 //that's 30m
        def test = [
          [ "http://saynext.redf4rth.net:8080/my-starter-app/prompt/font", /js>/ ],
          [ "http://geoserver.redf4rth.net/geoserver/web/", /org.geoserver.web.GeoServerBasePage/ ],
        ]

        def env = System.getenv()
        if (env['PAUSE_FOR_USERDATA_COMPLETION']) {
          println "Groovy is pausing: ${env['PAUSE_FOR_USERDATA_COMPLETION']}"
          sleep 1000*env['PAUSE_FOR_USERDATA_COMPLETION'].toInteger()
        }
        else {
          println "Groovy is pausing the default $DEFAULT_PAUSE_FOR_USERDATA_COMPLETION"
          sleep 1000*DEFAULT_PAUSE_FOR_USERDATA_COMPLETION
        }
/*        
        def phantom = [
          "BUILD_ID=dontKillMe",
          "/home/ubuntu/invoke-phantom",
          "gocontainer.redf4rth.net"
        ]
        .execute().text        
*/        
        def iRecognize = []
        for (int i=0; i<test.size(); i++) {
          def app = [
            "curl",  
            test[i][0]
          ]
          .execute().text
        
          iRecognize[i] = (app =~ test[i][1] )
        }

        boolean rc = true //iRecognize[0] && iRecognize[1]
        for (int i=0; i<test.size(); i++) {
          boolean b = iRecognize[i]
          println "$b ${test[i][0]}"
          rc = rc && b 
        }

        if (rc) {
          System.exit(0)
        }
        else {
          System.exit(-1)
        }
    }
}
