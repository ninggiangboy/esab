<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import SearchfieldDemo from '@/docs/examples/SearchfieldDemo.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Search field</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="SearchfieldDemo" container-class="max-w-[300px] mx-auto">
      <SearchfieldDemo />
    </DocComponentPreview>
  </article>
</template>
