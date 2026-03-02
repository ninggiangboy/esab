package dev.ngb.domain.thread.model.thread;

import dev.ngb.domain.DomainEntity;

/**
 * Open Graph preview metadata for a URL embedded in a thread.
 */
public class ThreadLinkPreview extends DomainEntity<Long> {

    private Long threadId;
    private String url;
    private String title;
    private String description;
    private String imageUrl;
}
