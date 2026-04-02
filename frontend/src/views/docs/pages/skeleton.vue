<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import SkeletonDemo from '@/docs/examples/SkeletonDemo.vue'
import SkeletonScreen from '@/docs/examples/SkeletonScreen.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Skeleton</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="SkeletonDemo">
      <SkeletonDemo />
    </DocComponentPreview>
    <h3>Screen</h3>
    <DocComponentPreview example="SkeletonScreen" container-class="max-w-[600px] mt-4 mx-auto">
      <SkeletonScreen />
    </DocComponentPreview>
  </article>
</template>
