<!--
  -    Copyright (c) 2018-2025, mrli All rights reserved.
  -
  - Redistribution and use in source and binary forms, with or without
  - modification, are permitted provided that the following conditions are met:
  -
  - Redistributions of source code must retain the above copyright notice,
  - this list of conditions and the following disclaimer.
  - Redistributions in binary form must reproduce the above copyright
  - notice, this list of conditions and the following disclaimer in the
  - documentation and/or other materials provided with the distribution.
  - Neither the name of the pig4cloud.com developer nor the names of its
  - contributors may be used to endorse or promote products derived from
  - this software without specific prior written permission.
  - Author: mrli
  -->
<template>
        <div class="wzh-wzhbill-edit">
                <basic-container>

        </basic-container>
    </div>
</template>

<script>
            import {addObj} from '@/api/wzh/wzhbill'
                    import {mapGetters} from 'vuex'
    import EditDown from "@/views/baseUtile/EditDown";
    export default {
        components: { EditDown},
        data() {
            return {

            }
        },
        computed: {
            ...mapGetters(['permissions'])
        },
        created() {

        },
        methods: {

        }
    }
</script>
<style lang="scss" scoped>
                .wzh-wzhbill-index {
                    .el-tag {
        width: 100%;
    }
    .el-divider--horizontal {
        margin: 10px 0;
    }
    .vxe-toolbar {
        height: 25px;
    }
    }
</style>