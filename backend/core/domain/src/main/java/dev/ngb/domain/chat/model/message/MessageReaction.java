package dev.ngb.domain.chat.model.message;

import dev.ngb.domain.DomainEntity;

/**
 * An emoji reaction on a message.
 */
public class MessageReaction extends DomainEntity<Long> {

    private Long messageId;
    private Long profileId;
    private String reaction;
}
