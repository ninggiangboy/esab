<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import TextAreaDemo from '@/docs/examples/TextAreaDemo.vue'
import TextAreaDisabled from '@/docs/examples/TextAreaDisabled.vue'
import TextAreaForm from '@/docs/examples/TextAreaForm.vue'
import TextAreaWithButton from '@/docs/examples/TextAreaWithButton.vue'
import TextAreaWithLabel from '@/docs/examples/TextAreaWithLabel.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Textarea</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="TextAreaDemo" container-class="max-w-[400px] mx-auto">
      <TextAreaDemo />
    </DocComponentPreview>
    <h3>Disabled</h3>
    <DocComponentPreview example="TextAreaDisabled" container-class="max-w-[400px] mx-auto">
      <TextAreaDisabled />
    </DocComponentPreview>
    <h3>With label</h3>
    <DocComponentPreview example="TextAreaWithLabel" container-class="max-w-[400px] mx-auto">
      <TextAreaWithLabel />
    </DocComponentPreview>
    <h3>With button</h3>
    <DocComponentPreview example="TextAreaWithButton" container-class="max-w-[400px] mx-auto">
      <TextAreaWithButton />
    </DocComponentPreview>
    <h3>Form</h3>
    <DocComponentPreview example="TextAreaForm" container-class="max-w-[400px] mx-auto">
      <TextAreaForm />
    </DocComponentPreview>
  </article>
</template>
