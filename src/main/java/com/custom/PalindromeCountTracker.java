package com.custom;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class PalindromeCountTracker extends AbstractBehavior<PalindromeCountTracker.PalindromeEvent> {
    private int palindromeCounter = 0;

    public static final class PalindromeEvent {
        boolean palindromeOccurred;

        public PalindromeEvent(boolean palindromeOccurred) {
            this.palindromeOccurred = palindromeOccurred;
        }

    }

    @Override
    public Receive<PalindromeEvent> createReceive() {
        return newReceiveBuilder().onMessage(PalindromeEvent.class, this::incrementPalindromeCounter).build();
    }

    private Behavior<PalindromeEvent> incrementPalindromeCounter(PalindromeEvent palindromeEvent) {
        if (palindromeEvent.palindromeOccurred) {
            palindromeCounter++;
        }
        getContext().getLog().info("Until now {} palindrome events have occurred", palindromeCounter);
        return Behaviors.same();
    }

    public PalindromeCountTracker(ActorContext<PalindromeEvent> context) {
        super(context);
    }

    public static Behavior<PalindromeCountTracker.PalindromeEvent> create() {
        return Behaviors.setup(PalindromeCountTracker::new);
    }
}
