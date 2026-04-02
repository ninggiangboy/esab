<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import TimeFieldDemo from '@/docs/examples/TimeFieldDemo.vue'
import TimeFieldDisabled from '@/docs/examples/TimeFieldDisabled.vue'
import TimeFieldForm from '@/docs/examples/TimeFieldForm.vue'
import TimeFieldGranularity from '@/docs/examples/TimeFieldGranularity.vue'
import TimeFieldWithLabel from '@/docs/examples/TimeFieldWithLabel.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Time field</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="TimeFieldDemo" container-class="max-w-[200px] mx-auto">
      <TimeFieldDemo />
    </DocComponentPreview>
    <h3>Disabled</h3>
    <DocComponentPreview example="TimeFieldDisabled" container-class="max-w-[200px] mx-auto">
      <TimeFieldDisabled />
    </DocComponentPreview>
    <h3>Granularity</h3>
    <DocComponentPreview example="TimeFieldGranularity" container-class="max-w-[300px] mx-auto">
      <TimeFieldGranularity />
    </DocComponentPreview>
    <h3>With label</h3>
    <DocComponentPreview example="TimeFieldWithLabel" container-class="max-w-[200px] mx-auto">
      <TimeFieldWithLabel />
    </DocComponentPreview>
    <h3>Form</h3>
    <DocComponentPreview example="TimeFieldForm" container-class="max-w-[300px] mx-auto">
      <TimeFieldForm />
    </DocComponentPreview>
  </article>
</template>
