<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import AvatarDemo from '@/docs/examples/AvatarDemo.vue'
import AvatarSizes from '@/docs/examples/AvatarSizes.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Avatar</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Demo</h2>
    <DocComponentPreview example="AvatarDemo">
      <AvatarDemo />
    </DocComponentPreview>
    <h3>Sizes</h3>
    <DocComponentPreview example="AvatarSizes">
      <AvatarSizes />
    </DocComponentPreview>
  </article>
</template>
