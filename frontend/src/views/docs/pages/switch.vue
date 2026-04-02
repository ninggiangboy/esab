<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import SwitchDemo from '@/docs/examples/SwitchDemo.vue'
import SwitchDisabled from '@/docs/examples/SwitchDisabled.vue'
import SwitchForm from '@/docs/examples/SwitchForm.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Switch</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="SwitchDemo" container-class="max-w-[300px] mx-auto">
      <SwitchDemo />
    </DocComponentPreview>
    <h3>Disabled</h3>
    <DocComponentPreview example="SwitchDisabled" container-class="max-w-[300px] mx-auto">
      <SwitchDisabled />
    </DocComponentPreview>
    <h3>Form</h3>
    <DocComponentPreview example="SwitchForm" container-class="max-w-[300px] mx-auto">
      <SwitchForm />
    </DocComponentPreview>
  </article>
</template>
