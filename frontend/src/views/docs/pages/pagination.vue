<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import PaginationDemo from '@/docs/examples/PaginationDemo.vue'
import PaginationWithPageSelector from '@/docs/examples/PaginationWithPageSelector.vue'
import PaginationWithQueryParams from '@/docs/examples/PaginationWithQueryParams.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Pagination</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="PaginationDemo">
      <PaginationDemo />
    </DocComponentPreview>
    <h3>Page size</h3>
    <DocComponentPreview example="PaginationWithPageSelector">
      <PaginationWithPageSelector />
    </DocComponentPreview>
    <h3>Query param</h3>
    <DocComponentPreview example="PaginationWithQueryParams">
      <PaginationWithQueryParams />
    </DocComponentPreview>
  </article>
</template>
