<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import NumberFieldCurrency from '@/docs/examples/NumberFieldCurrency.vue'
import NumberFieldDemo from '@/docs/examples/NumberFieldDemo.vue'
import NumberFieldDisabled from '@/docs/examples/NumberFieldDisabled.vue'
import NumberFieldForm from '@/docs/examples/NumberFieldForm.vue'
import NumberFieldMinMax from '@/docs/examples/NumberFieldMinMax.vue'
import NumberFieldPercentages from '@/docs/examples/NumberFieldPercentages.vue'
import NumberFieldUnits from '@/docs/examples/NumberFieldUnits.vue'
import NumberFieldWithLabel from '@/docs/examples/NumberFieldWithLabel.vue'
import NumberFieldWithoutStepper from '@/docs/examples/NumberFieldWithoutStepper.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Number field</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="NumberFieldDemo" container-class="max-w-[280px] mx-auto">
      <NumberFieldDemo />
    </DocComponentPreview>
    <h3>Without stepper</h3>
    <DocComponentPreview example="NumberFieldWithoutStepper" container-class="max-w-[280px] mx-auto">
      <NumberFieldWithoutStepper />
    </DocComponentPreview>
    <h3>Currency</h3>
    <DocComponentPreview example="NumberFieldCurrency" container-class="max-w-[280px] mx-auto">
      <NumberFieldCurrency />
    </DocComponentPreview>
    <h3>Percent</h3>
    <DocComponentPreview example="NumberFieldPercentages" container-class="max-w-[280px] mx-auto">
      <NumberFieldPercentages />
    </DocComponentPreview>
    <h3>Units</h3>
    <DocComponentPreview example="NumberFieldUnits" container-class="max-w-[280px] mx-auto">
      <NumberFieldUnits />
    </DocComponentPreview>
    <h3>Min / max</h3>
    <DocComponentPreview example="NumberFieldMinMax" container-class="max-w-[280px] mx-auto">
      <NumberFieldMinMax />
    </DocComponentPreview>
    <h3>Disabled</h3>
    <DocComponentPreview example="NumberFieldDisabled" container-class="max-w-[280px] mx-auto">
      <NumberFieldDisabled />
    </DocComponentPreview>
    <h3>With label</h3>
    <DocComponentPreview example="NumberFieldWithLabel" container-class="max-w-[280px] mx-auto">
      <NumberFieldWithLabel />
    </DocComponentPreview>
    <h3>Form</h3>
    <DocComponentPreview example="NumberFieldForm" container-class="max-w-[280px] mx-auto">
      <NumberFieldForm />
    </DocComponentPreview>
  </article>
</template>
