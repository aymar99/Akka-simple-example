package com.custom;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class PalindromePrinter extends AbstractBehavior<PalindromePrinter.Input> {

    public PalindromePrinter(ActorContext<Input> context) {
        super(context);
    }

    private boolean isPalindrome(String request) {
        boolean isPalindrome = false;
        StringBuilder reverseStr = new StringBuilder();
        int strLength = request.length();
        for (int i = (strLength - 1); i >= 0; --i) {
            reverseStr.append(request.charAt(i));
        }
        if (request.equalsIgnoreCase(reverseStr.toString())) {
            isPalindrome = true;
        }
        return isPalindrome;
    }

    private Behavior<Input> checkAndPrintIfPalindrome(Input input) {
        if (isPalindrome(input.message)) {
            input.replyTo.tell(new PalindromeCountTracker.PalindromeEvent(true));
            getContext().getLog().info("\"{}\" is a palindrome", input.message);
        } else {
            getContext().getLog().info("\"{}\" is not a palindrome", input.message);
        }
        return Behaviors.same();
    }

    public static final class Input {
        String message;
        ActorRef<PalindromeCountTracker.PalindromeEvent> replyTo;

        public Input(String message, ActorRef<PalindromeCountTracker.PalindromeEvent> replyTo) {
            this.message = message;
            this.replyTo = replyTo;
        }
    }

    public static Behavior<Input> create() {
        return Behaviors.setup(PalindromePrinter::new);
    }

    @Override
    public Receive<Input> createReceive() {
        return newReceiveBuilder().onMessage(Input.class, this::checkAndPrintIfPalindrome).build();
    }
}
