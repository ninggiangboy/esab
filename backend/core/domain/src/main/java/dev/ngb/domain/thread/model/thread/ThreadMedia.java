package dev.ngb.domain.thread.model.thread;

import dev.ngb.domain.DomainEntity;

/**
 * Media attachment on a thread (image, video, GIF) with dimensions and accessibility text.
 */
public class ThreadMedia extends DomainEntity<Long> {

    private Long threadId;

    private MediaType type;

    private String url;
    private String thumbnailUrl;

    private Integer width;
    private Integer height;
    private Integer duration;

    private String altText;
    private Integer position;
}
