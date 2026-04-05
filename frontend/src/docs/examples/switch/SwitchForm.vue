<script setup lang="ts">
import { docFormToast } from '@/docs/examples/_internal/docFormSubmit'
import { Button } from '@/ui/components/button'
import { Form, FormField, FormItem, FormLabel, FormVm, useForm } from '@/ui/components/form'
import { Switch } from '@/ui/components/switch-ui'
import { z } from 'zod'

const { handleSubmit } = useForm({ initialValues: { notify: true } })
const onSubmit = handleSubmit((v) => docFormToast(v))
</script>
<template>
  <Form class="space-y-4" @submit="onSubmit">
    <FormField
      v-slot="{ componentField }"
      name="notify"
      type="checkbox"
      :rules="z.boolean().ruleFn()"
    >
      <FormItem>
        <div class="flex items-center gap-2">
          <FormVm generic="boolean" v-slot="vm" :component-field="componentField">
            <Switch v-bind="vm" />
          </FormVm>
          <FormLabel class="!mt-0 cursor-pointer font-normal">Notifications</FormLabel>
        </div>
      </FormItem>
    </FormField>
    <Button type="submit">Submit</Button>
  </Form>
</template>
