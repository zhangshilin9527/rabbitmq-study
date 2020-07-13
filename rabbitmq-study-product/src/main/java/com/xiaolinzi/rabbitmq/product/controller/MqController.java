package com.xiaolinzi.rabbitmq.product.controller;

import com.xiaolinzi.rabbitmq.product.service.MqSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ：xiaolinzi
 * @date ：2019-3-20 19:00
 * @email: xiaolinzi95_27.com
 */
@RestController
public class MqController {
    private Logger logger = LoggerFactory.getLogger(MqController.class);

    @Resource
    private MqSendService mqSendService;


    @GetMapping(value = "/sendMsg", produces = "application/json")
    @ResponseBody
    public String queryCreditRecordByPhone() {

        try {
            mqSendService.sendMsg();
        } catch (Exception e) {
            logger.error("发送消息失败", e);
            return "fail";
        }

        return "success";
    }


}
