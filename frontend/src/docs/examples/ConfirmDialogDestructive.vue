<script setup lang="ts">
import { toast } from 'vue-sonner'

import UiButton from '@/components/ui/UiButton.vue'
import { confirm } from '@/lib/confirm-dialog'

function handleArchive() {
  confirm({
    variant: 'destructive',
    title: 'Archive Project',
    description: 'You can restore an archived project later from settings.',
    action: {
      label: 'Archive',
      onClick: () => {
        toast.success('Project Archived', {
          description: 'Project archived successfully',
        })
      },
    },
  })
}
</script>

<template>
  <UiButton variant="outline" type="button" @click="handleArchive">Archive Project</UiButton>
</template>
