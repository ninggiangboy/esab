<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import DateRangePickerDemo from '@/docs/examples/DateRangePickerDemo.vue'
import DateRangePickerDisabled from '@/docs/examples/DateRangePickerDisabled.vue'
import DateRangePickerForm from '@/docs/examples/DateRangePickerForm.vue'
import DateRangePickerWithLabel from '@/docs/examples/DateRangePickerWithLabel.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Date range picker</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="DateRangePickerDemo" container-class="max-w-[400px] mx-auto">
      <DateRangePickerDemo />
    </DocComponentPreview>
    <h3>Disabled</h3>
    <DocComponentPreview example="DateRangePickerDisabled" container-class="max-w-[400px] mx-auto">
      <DateRangePickerDisabled />
    </DocComponentPreview>
    <h3>With label</h3>
    <DocComponentPreview example="DateRangePickerWithLabel" container-class="max-w-[400px] mx-auto">
      <DateRangePickerWithLabel />
    </DocComponentPreview>
    <h3>Form</h3>
    <DocComponentPreview example="DateRangePickerForm" container-class="max-w-[400px] mx-auto">
      <DateRangePickerForm />
    </DocComponentPreview>
  </article>
</template>
