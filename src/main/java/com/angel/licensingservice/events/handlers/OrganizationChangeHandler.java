package com.angel.licensingservice.events.handlers;

import com.angel.licensingservice.LicensingServiceApplication;
import com.angel.licensingservice.events.channels.CustomChannels;
import com.angel.licensingservice.events.models.OrganizationChangedModel;
import com.angel.licensingservice.repository.OrganizationRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(CustomChannels.class)
public class OrganizationChangeHandler {

    private Logger logger = LoggerFactory.getLogger(LicensingServiceApplication.class);

    @Autowired
    private OrganizationRedisRepository organizationRedisRepository;


    @StreamListener("inboundOrgChanges")
    public void loggerSink(OrganizationChangedModel orgChange) {

        switch (orgChange.getAction()) {
            case "UPDATE":
                logger.error("Received an event for organization id {} for action UPDATE", orgChange.getOrganizationId());
                organizationRedisRepository.deleteOrganization(orgChange.getOrganizationId());
                break;
            case "DELETE":
                logger.error("Received an event for organization id {} for action DELETE", orgChange.getOrganizationId());
                organizationRedisRepository.deleteOrganization(orgChange.getOrganizationId());
                break;
        }
    }
}
