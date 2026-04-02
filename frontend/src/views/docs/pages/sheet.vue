<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import SheetDemo from '@/docs/examples/SheetDemo.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Sheet</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="SheetDemo">
      <SheetDemo />
    </DocComponentPreview>
  </article>
</template>
