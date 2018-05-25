package be.jstack.ticketing.controller.ticket;

import be.jstack.ticketing.entities.ticketing.Topic;
import be.jstack.ticketing.service.ticket.TopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/topics")
@Api(value = "Topic controller", description = "View info about all the topics")
public class TopicController {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    @ApiOperation(value = "View a list of all the topics", response = Topic.class, responseContainer = "List")
    public List<Topic> findAllTopics() {
        return Lists.newArrayList(topicService.findAllTopics());
    }
}