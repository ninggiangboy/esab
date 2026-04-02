<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import AccordionDemo from '@/docs/examples/AccordionDemo.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Accordion</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="AccordionDemo" container-class="max-w-[450px] mx-auto">
      <AccordionDemo />
    </DocComponentPreview>
  </article>
</template>
