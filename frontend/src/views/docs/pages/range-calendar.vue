<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import RangeCalendarControlled from '@/docs/examples/RangeCalendarControlled.vue'
import RangeCalendarDefault from '@/docs/examples/RangeCalendarDefault.vue'
import RangeCalendarDemo from '@/docs/examples/RangeCalendarDemo.vue'
import RangeCalendarMinMax from '@/docs/examples/RangeCalendarMinMax.vue'
import RangeCalendarUnstyled from '@/docs/examples/RangeCalendarUnstyled.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Range calendar</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="RangeCalendarDemo" container-class="max-w-[340px] mx-auto">
      <RangeCalendarDemo />
    </DocComponentPreview>
    <h3>Default</h3>
    <DocComponentPreview example="RangeCalendarDefault" container-class="max-w-[340px] mx-auto">
      <RangeCalendarDefault />
    </DocComponentPreview>
    <h3>Controlled</h3>
    <DocComponentPreview example="RangeCalendarControlled" container-class="max-w-[340px] mx-auto">
      <RangeCalendarControlled />
    </DocComponentPreview>
    <h3>Unstyled</h3>
    <DocComponentPreview example="RangeCalendarUnstyled" container-class="max-w-[340px] mx-auto">
      <RangeCalendarUnstyled />
    </DocComponentPreview>
    <h3>Min / max</h3>
    <DocComponentPreview example="RangeCalendarMinMax" container-class="max-w-[340px] mx-auto">
      <RangeCalendarMinMax />
    </DocComponentPreview>
  </article>
</template>
