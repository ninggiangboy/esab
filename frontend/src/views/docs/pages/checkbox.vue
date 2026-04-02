<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import CheckboxDemo from '@/docs/examples/CheckboxDemo.vue'
import CheckboxDisabled from '@/docs/examples/CheckboxDisabled.vue'
import CheckboxForm from '@/docs/examples/CheckboxForm.vue'
import CheckboxGroupDemo from '@/docs/examples/CheckboxGroupDemo.vue'
import CheckboxIndeterminate from '@/docs/examples/CheckboxIndeterminate.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Checkbox</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="CheckboxDemo">
      <CheckboxDemo />
    </DocComponentPreview>
    <h3>Disabled</h3>
    <DocComponentPreview example="CheckboxDisabled">
      <CheckboxDisabled />
    </DocComponentPreview>
    <h3>Indeterminate</h3>
    <DocComponentPreview example="CheckboxIndeterminate">
      <CheckboxIndeterminate />
    </DocComponentPreview>
    <h3>Group</h3>
    <DocComponentPreview example="CheckboxGroupDemo">
      <CheckboxGroupDemo />
    </DocComponentPreview>
    <h3>Form</h3>
    <DocComponentPreview example="CheckboxForm">
      <CheckboxForm />
    </DocComponentPreview>
  </article>
</template>
