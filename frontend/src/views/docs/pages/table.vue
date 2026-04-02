<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import TableDemo from '@/docs/examples/TableDemo.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Table</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="TableDemo">
      <TableDemo />
    </DocComponentPreview>
  </article>
</template>
