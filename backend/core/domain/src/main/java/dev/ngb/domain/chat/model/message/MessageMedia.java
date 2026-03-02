package dev.ngb.domain.chat.model.message;

import dev.ngb.domain.DomainEntity;

/**
 * Links a message to a media asset managed by the media service.
 */
public class MessageMedia extends DomainEntity<Long> {

    private Long messageId;
    private Long mediaId;
}
