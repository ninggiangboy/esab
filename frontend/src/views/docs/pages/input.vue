<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import InputDemo from '@/docs/examples/InputDemo.vue'
import InputDisabled from '@/docs/examples/InputDisabled.vue'
import InputFile from '@/docs/examples/InputFile.vue'
import InputForm from '@/docs/examples/InputForm.vue'
import InputWithButton from '@/docs/examples/InputWithButton.vue'
import InputWithLabel from '@/docs/examples/InputWithLabel.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Input</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="InputDemo" container-class="max-w-[300px] mx-auto">
      <InputDemo />
    </DocComponentPreview>
    <h3>File</h3>
    <DocComponentPreview example="InputFile" container-class="max-w-[300px] mx-auto">
      <InputFile />
    </DocComponentPreview>
    <h3>Disabled</h3>
    <DocComponentPreview example="InputDisabled" container-class="max-w-[300px] mx-auto">
      <InputDisabled />
    </DocComponentPreview>
    <h3>With label</h3>
    <DocComponentPreview example="InputWithLabel" container-class="max-w-[300px] mx-auto">
      <InputWithLabel />
    </DocComponentPreview>
    <h3>With button</h3>
    <DocComponentPreview example="InputWithButton" container-class="max-w-[400px] mx-auto">
      <InputWithButton />
    </DocComponentPreview>
    <h3>Form</h3>
    <DocComponentPreview example="InputForm" container-class="max-w-[400px] mx-auto">
      <InputForm />
    </DocComponentPreview>
  </article>
</template>
