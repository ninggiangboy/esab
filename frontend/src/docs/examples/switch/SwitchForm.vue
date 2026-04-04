<script setup lang="ts">
import { docFormToast } from '@/docs/examples/_internal/docFormSubmit'
import { toTypedSchema } from '@vee-validate/zod'
import { z } from 'zod'
import { Button } from '@/ui/components/button'
import {
  Form,
  FormField,
  FormItem,
  FormLabel,
  useForm,
} from '@/ui/components/form'
import { Switch } from '@/ui/components/switch-ui'

const schema = toTypedSchema(z.object({ notify: z.boolean() }))
const { handleSubmit } = useForm({ validationSchema: schema, initialValues: { notify: true } })
const onSubmit = handleSubmit((v) => docFormToast(v))
</script>
<template>
  <Form class="space-y-4" @submit="onSubmit">
    <FormField v-slot="{ componentField }" name="notify" type="checkbox">
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
