<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import CalendarControlled from '@/docs/examples/CalendarControlled.vue'
import CalendarDemo from '@/docs/examples/CalendarDemo.vue'
import CalendarMinMax from '@/docs/examples/CalendarMinMax.vue'
import CalendarUnstyled from '@/docs/examples/CalendarUnstyled.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Calendar</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="CalendarDemo" container-class="max-w-[360px] mx-auto">
      <CalendarDemo />
    </DocComponentPreview>
    <h3>Controlled</h3>
    <DocComponentPreview example="CalendarControlled" container-class="max-w-[360px] mx-auto">
      <CalendarControlled />
    </DocComponentPreview>
    <h3>Unstyled</h3>
    <DocComponentPreview example="CalendarUnstyled" container-class="max-w-[360px] mx-auto">
      <CalendarUnstyled />
    </DocComponentPreview>
    <h3>Min / max</h3>
    <DocComponentPreview example="CalendarMinMax" container-class="max-w-[360px] mx-auto">
      <CalendarMinMax />
    </DocComponentPreview>
  </article>
</template>
