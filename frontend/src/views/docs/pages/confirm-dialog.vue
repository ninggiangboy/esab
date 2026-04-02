<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import ConfirmDialogDemo from '@/docs/examples/ConfirmDialogDemo.vue'
import ConfirmDialogDestructive from '@/docs/examples/ConfirmDialogDestructive.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Confirm dialog</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="ConfirmDialogDemo">
      <ConfirmDialogDemo />
    </DocComponentPreview>
    <h3>Destructive</h3>
    <DocComponentPreview example="ConfirmDialogDestructive">
      <ConfirmDialogDestructive />
    </DocComponentPreview>
  </article>
</template>
