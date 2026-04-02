<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import FileTriggerAvatar from '@/docs/examples/FileTriggerAvatar.vue'
import FileTriggerDemo from '@/docs/examples/FileTriggerDemo.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">File trigger</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="FileTriggerDemo">
      <FileTriggerDemo />
    </DocComponentPreview>
    <h3>Avatar pick</h3>
    <DocComponentPreview example="FileTriggerAvatar">
      <FileTriggerAvatar />
    </DocComponentPreview>
  </article>
</template>
