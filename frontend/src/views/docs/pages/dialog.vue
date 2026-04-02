<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import DialogDemo from '@/docs/examples/DialogDemo.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Dialog</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="DialogDemo">
      <DialogDemo />
    </DocComponentPreview>
  </article>
</template>
