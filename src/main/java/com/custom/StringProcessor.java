package com.custom;


import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringProcessor extends AbstractBehavior<StringProcessor.Request> {

    public StringProcessor(ActorContext<Request> context) {
        super(context);
    }

    public static final class Request {
        String paragraph;

        public Request(String paragraph) {
            this.paragraph = paragraph;
        }
    }

    public static Behavior<Request> create() {
        return Behaviors.setup(StringProcessor::new);
    }

    @Override
    public Receive<Request> createReceive() {
        return newReceiveBuilder().onMessage(Request.class, this::processInput).build();
    }

    private Behavior<Request> processInput(Request request) {
        getContext().getLog().info("Processing paragraph....{}", request.paragraph);
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(request.paragraph);
        ActorRef<PalindromeCountTracker.PalindromeEvent> palindromeTracker =
                getContext().spawn(PalindromeCountTracker.create(), "palindromeTracker");
        while (matcher.find()) {
            ActorRef<PalindromePrinter.Input> replyTo =
                    getContext().spawn(PalindromePrinter.create(), matcher.group() + Math.random());
            replyTo.tell(new PalindromePrinter.Input(matcher.group(), palindromeTracker));
        }
        return Behaviors.same();
    }
}