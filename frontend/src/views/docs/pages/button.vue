<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import ButtonAsALink from '@/docs/examples/ButtonAsALink.vue'
import ButtonDemo from '@/docs/examples/ButtonDemo.vue'
import ButtonDestructive from '@/docs/examples/ButtonDestructive.vue'
import ButtonGhost from '@/docs/examples/ButtonGhost.vue'
import ButtonIcon from '@/docs/examples/ButtonIcon.vue'
import ButtonLoading from '@/docs/examples/ButtonLoading.vue'
import ButtonOutline from '@/docs/examples/ButtonOutline.vue'
import ButtonWithIcon from '@/docs/examples/ButtonWithIcon.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Button</h1>
    <p> A button triggers an action. Use <code>UiButton</code> or <code>buttonVariants</code> on links. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="ButtonDemo">
      <ButtonDemo />
    </DocComponentPreview>
    <h3>Outline</h3>
    <DocComponentPreview example="ButtonOutline">
      <ButtonOutline />
    </DocComponentPreview>
    <h3>Destructive</h3>
    <DocComponentPreview example="ButtonDestructive">
      <ButtonDestructive />
    </DocComponentPreview>
    <h3>Ghost</h3>
    <DocComponentPreview example="ButtonGhost">
      <ButtonGhost />
    </DocComponentPreview>
    <h3>Icon</h3>
    <DocComponentPreview example="ButtonIcon">
      <ButtonIcon />
    </DocComponentPreview>
    <h3>With icon</h3>
    <DocComponentPreview example="ButtonWithIcon">
      <ButtonWithIcon />
    </DocComponentPreview>
    <h3>As a link</h3>
    <DocComponentPreview example="ButtonAsALink">
      <ButtonAsALink />
    </DocComponentPreview>
    <h3>Loading</h3>
    <DocComponentPreview example="ButtonLoading">
      <ButtonLoading />
    </DocComponentPreview>
  </article>
</template>
