<script setup lang="ts">
import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'
import DataTableColumnAlignment from '@/docs/examples/DataTableColumnAlignment.vue'
import DataTableDemo from '@/docs/examples/DataTableDemo.vue'
import DataTableLoadingState from '@/docs/examples/DataTableLoadingState.vue'
import DataTableRealworld from '@/docs/examples/DataTableRealworld.vue'
import DataTableRowSelection from '@/docs/examples/DataTableRowSelection.vue'
import DataTableSorting from '@/docs/examples/DataTableSorting.vue'
import DataTableSticky from '@/docs/examples/DataTableSticky.vue'
</script>

<template>
  <article class="prose dark:prose-invert max-w-none">
    <h1 class="text-foreground">Data table</h1>
    <p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>
    <h2>Usage</h2>
    <DocComponentPreview example="DataTableDemo">
      <DataTableDemo />
    </DocComponentPreview>
    <h3>Column alignment</h3>
    <DocComponentPreview example="DataTableColumnAlignment">
      <DataTableColumnAlignment />
    </DocComponentPreview>
    <h3>Loading</h3>
    <DocComponentPreview example="DataTableLoadingState">
      <DataTableLoadingState />
    </DocComponentPreview>
    <h3>Row selection</h3>
    <DocComponentPreview example="DataTableRowSelection">
      <DataTableRowSelection />
    </DocComponentPreview>
    <h3>Sorting</h3>
    <DocComponentPreview example="DataTableSorting">
      <DataTableSorting />
    </DocComponentPreview>
    <h3>Sticky columns</h3>
    <DocComponentPreview example="DataTableSticky">
      <DataTableSticky />
    </DocComponentPreview>
    <h3>Kitchen sink</h3>
    <DocComponentPreview example="DataTableRealworld">
      <DataTableRealworld />
    </DocComponentPreview>
  </article>
</template>
