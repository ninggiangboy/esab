<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import TooltipBasic from '@/docs/examples/TooltipBasic.vue'
import TooltipCustom from '@/docs/examples/TooltipCustom.vue'
import TooltipDemo from '@/docs/examples/TooltipDemo.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Tooltip</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="TooltipDemo">
      <TooltipDemo />
    </DocComponentPreview>
    <h3>Basic</h3>
    <DocComponentPreview example="TooltipBasic">
      <TooltipBasic />
    </DocComponentPreview>
    <h3>Custom</h3>
    <DocComponentPreview example="TooltipCustom">
      <TooltipCustom />
    </DocComponentPreview>
  </article>
</template>
