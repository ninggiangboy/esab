<script setup lang="ts">
import { toast } from 'vue-sonner'

import UiButton from '@/components/ui/UiButton.vue'
import { confirm } from '@/lib/confirm-dialog'

function handleDeleteUsers() {
  confirm({
    title: 'Delete Users',
    description: 'Are you sure you want to delete these users?',
    action: {
      label: 'Delete',
      onClick: () => {
        toast.success('Users Deleted', {
          description: 'Users deleted successfully',
        })
      },
    },
  })
}
</script>

<template>
  <UiButton variant="outline" type="button" @click="handleDeleteUsers">Delete Users</UiButton>
</template>
