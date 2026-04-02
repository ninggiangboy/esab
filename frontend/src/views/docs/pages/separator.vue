<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import SeparatorDemo from '@/docs/examples/SeparatorDemo.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Separator</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="SeparatorDemo" container-class="max-w-[300px] mx-auto">
      <SeparatorDemo />
    </DocComponentPreview>
  </article>
</template>
