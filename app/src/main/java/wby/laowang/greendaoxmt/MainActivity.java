package wby.laowang.greendaoxmt;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import wby.laowang.greendaoxmt.gen.DUserDao;
import wby.laowang.greendaoxmt.gen.DaoMaster;
import wby.laowang.greendaoxmt.gen.DaoSession;
import wby.laowang.greendaoxmt.recycler.MyAdapter;

public class MainActivity extends AppCompatActivity {

    private EditText ed_name;
    private EditText ed_age;
    private EditText ed_id;
    private RecyclerView dao_rec;
    private DUserDao dUserDao;
    private String name;
    private String age;
    private MyAdapter myAdapter;
    View viewm;
    private List<DUser> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        DaoUse();

    }

    public void initViews() {

        ed_name = findViewById(R.id.ed_name);
        ed_age = findViewById(R.id.ed_age);
        ed_id = findViewById(R.id.ed_id);
        dao_rec = findViewById(R.id.dao_rec);
    }


    public void DaoUse() {

        /*在自己的Application中先创建了一个SQLiteOpenHelper并创建连接到一个具体数据库；
        再根据具体的datebase创建一个master对象用于；最后通过master创建一个数据库的会话操作。*/

        //创建数据库
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "User.db", null);
        //得到数据库连接对象
        SQLiteDatabase database = openHelper.getWritableDatabase();
        //得到数据库管理者
        DaoMaster daoMaster = new DaoMaster(database);
        //得到daoSession，可以执行增删改查操作
        DaoSession daoSession = daoMaster.newSession();

        dUserDao = daoSession.getDUserDao();

    }

    public void daoselect(View view) {

        list = dUserDao.queryBuilder().list();
        dao_rec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        myAdapter = new MyAdapter(list, this);
        ed_name.setText("");
        ed_age.setText("");
        dao_rec.setAdapter(myAdapter);
        cliupdate();

    }

    public void daoadd(View view) {

        name = ed_name.getText().toString();
        age = ed_age.getText().toString();
        if(name.isEmpty() && age.isEmpty()){
            Toast.makeText(MainActivity.this,"请输入内容",Toast.LENGTH_SHORT).show();
        }else {
            dUserDao.insert(new DUser(null, name, age));
            ed_name.setText("");
            ed_age.setText("");
            daoselect(view);
        }
    }

    public void daodelete(View view) {

        /* int xid = Integer.parseInt(sid);
         * 这个是把String型数据更改为int型*/

        /*直接全部删除
        dUserDao.deleteAll();*/

        String sid = ed_id.getText().toString();
        dUserDao.deleteByKey(Long.valueOf(sid));
        ed_name.setText("");
        ed_age.setText("");
        ed_id.setText("");
        daoselect(view);
    }

    public void cliupdate() {

        myAdapter.setOnItemLinister(new MyAdapter.OnItemLinister() {
            @Override
            public void OnSetItem(final int postion) {

                final View viewp = View.inflate(MainActivity.this, R.layout.xiu_item, null);
                final EditText xiu_name = viewp.findViewById(R.id.xiu_name);
                final EditText xiu_age = viewp.findViewById(R.id.xiu_age);

                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("确认要修改吗?")
                        .setView(viewp)
                        .setPositiveButton("是的", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String sname = xiu_name.getText().toString();
                                String sage = xiu_age.getText().toString();

                                //这个是一种修改办法
                                /*DUser dUser = dUserDao.queryBuilder().where(DUserDao.Properties.Id.eq(list.get(postion).getId())).build().unique();
                                if (dUser!=null){
                                    dUser.setName(sname);
                                    dUser.setAge(sage);
                                }*/
                                DUser load = dUserDao.load(Long.valueOf(list.get(postion).getId()));
                                load.setName(sname);
                                load.setAge(sage);
                                dUserDao.update(load);
                                daoselect(viewm);
                            }
                        })
                        .setNegativeButton("否", null)
                        .show();

                dialog.show();
            }
        });
    }

}
