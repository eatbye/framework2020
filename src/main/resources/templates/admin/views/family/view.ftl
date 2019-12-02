<div class="layui-fluid" id="form" style="margin-top:10px">
    <table class="layui-table">
        <tbody>
        <tr>
            <td>姓名</td>
            <td style="text-align: left">${family.name}</td>
            <td>城市</td>
            <td style="text-align: left"> <@dict dictName="城市" value="${family.city}"> </@dict></td>
        </tr>
        <tr>
            <td>地址</td>
            <td style="text-align: left" colspan="3">${family.address}</td>
        </tr>
        <tr>
            <td>面积</td>
            <td style="text-align: left" colspan="3">${family.area}</td>
        </tr>
        </tbody>
    </table>

</div>
