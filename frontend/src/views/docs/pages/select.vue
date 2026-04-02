<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import SelectCustom from '@/docs/examples/SelectCustom.vue'
import SelectDemo from '@/docs/examples/SelectDemo.vue'
import SelectDisabled from '@/docs/examples/SelectDisabled.vue'
import SelectForm from '@/docs/examples/SelectForm.vue'
import SelectMultiple from '@/docs/examples/SelectMultiple.vue'
import SelectMultipleCustomization from '@/docs/examples/SelectMultipleCustomization.vue'
import SelectMultipleDisabled from '@/docs/examples/SelectMultipleDisabled.vue'
import SelectMultipleSearchable from '@/docs/examples/SelectMultipleSearchable.vue'
import SelectSearchable from '@/docs/examples/SelectSearchable.vue'
import SelectWithClearButton from '@/docs/examples/SelectWithClearButton.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Select</h1>
    <p>
      Live examples mirroring the sample docs. Single selection uses <code>UiSelect</code> (Radix). Multiple selection
      uses <code>UiNativeSelect</code> — Radix Vue Select is single-value only. Implementation:
      <code>src/components/ui</code>.
    </p>
    <h2>Usage</h2>
    <DocComponentPreview example="SelectDemo" container-class="max-w-[300px] mx-auto">
      <SelectDemo />
    </DocComponentPreview>
    <h3>Disabled</h3>
    <DocComponentPreview example="SelectDisabled" container-class="max-w-[300px] mx-auto">
      <SelectDisabled />
    </DocComponentPreview>
    <h3>Searchable</h3>
    <DocComponentPreview example="SelectSearchable" container-class="max-w-[300px] mx-auto">
      <SelectSearchable />
    </DocComponentPreview>
    <h3>Groups</h3>
    <DocComponentPreview example="SelectCustom" container-class="max-w-[300px] mx-auto">
      <SelectCustom />
    </DocComponentPreview>
    <h3>Multiple</h3>
    <DocComponentPreview example="SelectMultiple" container-class="max-w-[350px] mx-auto">
      <SelectMultiple />
    </DocComponentPreview>
    <h3>Multiple disabled</h3>
    <DocComponentPreview example="SelectMultipleDisabled" container-class="max-w-[350px] mx-auto">
      <SelectMultipleDisabled />
    </DocComponentPreview>
    <h3>Multiple searchable</h3>
    <DocComponentPreview example="SelectMultipleSearchable" container-class="max-w-[350px] mx-auto">
      <SelectMultipleSearchable />
    </DocComponentPreview>
    <h3>Styled options</h3>
    <DocComponentPreview example="SelectMultipleCustomization" container-class="max-w-[350px] mx-auto">
      <SelectMultipleCustomization />
    </DocComponentPreview>
    <h3>With clear</h3>
    <DocComponentPreview example="SelectWithClearButton" container-class="max-w-[350px] mx-auto">
      <SelectWithClearButton />
    </DocComponentPreview>
    <h3>Form</h3>
    <DocComponentPreview example="SelectForm" container-class="max-w-[350px] mx-auto">
      <SelectForm />
    </DocComponentPreview>
  </article>
</template>
