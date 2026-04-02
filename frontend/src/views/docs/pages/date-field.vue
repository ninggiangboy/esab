<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import DateFieldDemo from '@/docs/examples/DateFieldDemo.vue'
import DateFieldDisabled from '@/docs/examples/DateFieldDisabled.vue'
import DateFieldForm from '@/docs/examples/DateFieldForm.vue'
import DateFieldWithLabel from '@/docs/examples/DateFieldWithLabel.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Date field</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="DateFieldDemo" container-class="max-w-[280px] mx-auto">
      <DateFieldDemo />
    </DocComponentPreview>
    <h3>Disabled</h3>
    <DocComponentPreview example="DateFieldDisabled" container-class="max-w-[280px] mx-auto">
      <DateFieldDisabled />
    </DocComponentPreview>
    <h3>With label</h3>
    <DocComponentPreview example="DateFieldWithLabel" container-class="max-w-[280px] mx-auto">
      <DateFieldWithLabel />
    </DocComponentPreview>
    <h3>Form</h3>
    <DocComponentPreview example="DateFieldForm" container-class="max-w-[280px] mx-auto">
      <DateFieldForm />
    </DocComponentPreview>
  </article>
</template>
