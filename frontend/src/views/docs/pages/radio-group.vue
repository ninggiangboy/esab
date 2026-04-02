<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import RadioGroupDemo from '@/docs/examples/RadioGroupDemo.vue'
import RadioGroupDisabled from '@/docs/examples/RadioGroupDisabled.vue'
import RadioGroupForm from '@/docs/examples/RadioGroupForm.vue'
import RadioGroupHorizontal from '@/docs/examples/RadioGroupHorizontal.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Radio group</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="RadioGroupDemo">
      <RadioGroupDemo />
    </DocComponentPreview>
    <h3>Horizontal</h3>
    <DocComponentPreview example="RadioGroupHorizontal">
      <RadioGroupHorizontal />
    </DocComponentPreview>
    <h3>Disabled</h3>
    <DocComponentPreview example="RadioGroupDisabled">
      <RadioGroupDisabled />
    </DocComponentPreview>
    <h3>Form</h3>
    <DocComponentPreview example="RadioGroupForm" container-class="max-w-[300px] mx-auto">
      <RadioGroupForm />
    </DocComponentPreview>
  </article>
</template>
