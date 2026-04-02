<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import MenuDemo from '@/docs/examples/MenuDemo.vue'
import MenuWithIcon from '@/docs/examples/MenuWithIcon.vue'
import MenuWithKeyboard from '@/docs/examples/MenuWithKeyboard.vue'
import MenuWithSubmenu from '@/docs/examples/MenuWithSubmenu.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Menu</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="MenuDemo">
      <MenuDemo />
    </DocComponentPreview>
    <h3>With icons</h3>
    <DocComponentPreview example="MenuWithIcon">
      <MenuWithIcon />
    </DocComponentPreview>
    <h3>Submenu</h3>
    <DocComponentPreview example="MenuWithSubmenu">
      <MenuWithSubmenu />
    </DocComponentPreview>
    <h3>Shortcuts</h3>
    <DocComponentPreview example="MenuWithKeyboard">
      <MenuWithKeyboard />
    </DocComponentPreview>
  </article>
</template>
