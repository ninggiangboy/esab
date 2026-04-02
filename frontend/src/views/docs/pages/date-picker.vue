<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import DatePickerDemo from '@/docs/examples/DatePickerDemo.vue'
import DatePickerDisabled from '@/docs/examples/DatePickerDisabled.vue'
import DatePickerForm from '@/docs/examples/DatePickerForm.vue'
import DatePickerWithLabel from '@/docs/examples/DatePickerWithLabel.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Date picker</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="DatePickerDemo" container-class="max-w-[400px] mx-auto">
      <DatePickerDemo />
    </DocComponentPreview>
    <h3>Disabled</h3>
    <DocComponentPreview example="DatePickerDisabled" container-class="max-w-[400px] mx-auto">
      <DatePickerDisabled />
    </DocComponentPreview>
    <h3>With label</h3>
    <DocComponentPreview example="DatePickerWithLabel" container-class="max-w-[400px] mx-auto">
      <DatePickerWithLabel />
    </DocComponentPreview>
    <h3>Form</h3>
    <DocComponentPreview example="DatePickerForm" container-class="max-w-[400px] mx-auto">
      <DatePickerForm />
    </DocComponentPreview>
  </article>
</template>
