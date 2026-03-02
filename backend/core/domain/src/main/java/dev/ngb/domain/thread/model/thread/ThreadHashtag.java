package dev.ngb.domain.thread.model.thread;

import dev.ngb.domain.DomainEntity;

/**
 * Maps a thread to a hashtag, with position tracking within the thread content.
 */
public class ThreadHashtag extends DomainEntity<Long> {

    private Long threadId;
    private Long hashtagId;
    private Integer positionStart;
    private Integer positionEnd;
}
