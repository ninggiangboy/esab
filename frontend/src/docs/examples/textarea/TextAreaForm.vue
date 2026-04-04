<script setup lang="ts">
import { toTypedSchema } from '@vee-validate/zod'
import { z } from 'zod'
import { docFormToast } from '@/docs/examples/_internal/docFormSubmit'
import { Button } from '@/ui/components/button'
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  useForm,
} from '@/ui/components/form'
import { TextArea } from '@/ui/components/textfield'

const schema = toTypedSchema(z.object({ bio: z.string().min(2) }))
const { handleSubmit } = useForm({
  validationSchema: schema,
  initialValues: { bio: '' },
})
const onSubmit = handleSubmit((v) => docFormToast(v))
</script>

<template>
  <Form class="w-full space-y-4" @submit="onSubmit">
    <FormField v-slot="{ componentField }" name="bio">
      <FormItem>
        <FormLabel>Bio</FormLabel>
        <FormControl v-slot="controlProps">
          <TextArea
            v-bind="{ ...componentField, ...controlProps }"
            placeholder="Type your bio here..."
            class="min-h-24"
          />
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>
    <Button type="submit"> Submit </Button>
  </Form>
</template>
