<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import PopoverBottomRight from '@/docs/examples/PopoverBottomRight.vue'
import PopoverDemo from '@/docs/examples/PopoverDemo.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Popover</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="PopoverDemo">
      <PopoverDemo />
    </DocComponentPreview>
    <h3>Bottom end</h3>
    <DocComponentPreview example="PopoverBottomRight">
      <PopoverBottomRight />
    </DocComponentPreview>
  </article>
</template>
