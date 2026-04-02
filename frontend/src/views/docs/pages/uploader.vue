<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import UploaderCustom from '@/docs/examples/UploaderCustom.vue'
import UploaderDemo from '@/docs/examples/UploaderDemo.vue'
import UploaderDisabled from '@/docs/examples/UploaderDisabled.vue'
import UploaderForm from '@/docs/examples/UploaderForm.vue'
import UploaderRetry from '@/docs/examples/UploaderRetry.vue'
import UploaderValidation from '@/docs/examples/UploaderValidation.vue'
import UploaderVariantListType from '@/docs/examples/UploaderVariantListType.vue'
import UploaderVariantTriggerType from '@/docs/examples/UploaderVariantTriggerType.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Uploader</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="UploaderDemo" container-class="max-w-[500px] mx-auto">
      <UploaderDemo />
    </DocComponentPreview>
    <h3>Custom</h3>
    <DocComponentPreview example="UploaderCustom" container-class="max-w-[500px] mx-auto">
      <UploaderCustom />
    </DocComponentPreview>
    <h3>Retry</h3>
    <DocComponentPreview example="UploaderRetry" container-class="max-w-[500px] mx-auto">
      <UploaderRetry />
    </DocComponentPreview>
    <h3>Disabled</h3>
    <DocComponentPreview example="UploaderDisabled" container-class="max-w-[500px] mx-auto">
      <UploaderDisabled />
    </DocComponentPreview>
    <h3>List layout</h3>
    <DocComponentPreview example="UploaderVariantListType" container-class="max-w-[500px] mx-auto">
      <UploaderVariantListType />
    </DocComponentPreview>
    <h3>Trigger layout</h3>
    <DocComponentPreview example="UploaderVariantTriggerType" container-class="max-w-[500px] mx-auto">
      <UploaderVariantTriggerType />
    </DocComponentPreview>
    <h3>Validation</h3>
    <DocComponentPreview example="UploaderValidation" container-class="max-w-[500px] mx-auto">
      <UploaderValidation />
    </DocComponentPreview>
    <h3>Form</h3>
    <DocComponentPreview example="UploaderForm" container-class="max-w-[500px] mx-auto">
      <UploaderForm />
    </DocComponentPreview>
  </article>
</template>
