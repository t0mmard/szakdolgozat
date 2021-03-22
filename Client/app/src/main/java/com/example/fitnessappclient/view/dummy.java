package com.example.fitnessappclient.view;

public class dummy {
    /*
        val customList = arrayOf("egy", "ketto")
        val adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, customList)
        spinnerMonths.adapter = adapter

        spinnerMonths.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@MainActivity, "You selected ${parent?.getItemAtPosition(position).toString()}", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {           }
        }

        val addContactDialog = AlertDialog.Builder(this)
            .setTitle("Add contact")
            .setMessage("Hozzáadod a szemétládát?")
            .setIcon(R.drawable.ic_sports)
            .setPositiveButton("Igen"){ _, _ ->
                Toast.makeText(this,"hozzáadtad",Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Nope"){ _, _ ->
                Toast.makeText(this,"nem adtad",Toast.LENGTH_SHORT).show()
            }.create()

        val options = arrayOf("elso","masodik","harmadik")
        val singleChoiceDialog = AlertDialog.Builder(this)
            .setTitle("Válassz")
            .setSingleChoiceItems(options, 0) { dialog, which ->
                Toast.makeText(this, "Erre nyomtál: ${options[which]}", Toast.LENGTH_SHORT).show()
            }
            .setIcon(R.drawable.ic_sports)
            .setPositiveButton("Igen"){ _, _ ->
                Toast.makeText(this,"hozzáadtad",Toast.LENGTH_SHORT).show()
            }.create()

        alert1.setOnClickListener {
            //addContactDialog.show()
            singleChoiceDialog.show()
        }

        val multipleChoiceDialog = AlertDialog.Builder(this)
            .setTitle("Válassz")
            .setMultiChoiceItems(options, booleanArrayOf(true,false,false)) { _, which, isChecked ->
                Toast.makeText(this, "Erre nyomtál: ${options[which]}, erre változott: $isChecked", Toast.LENGTH_SHORT).show()
            }
            .setIcon(R.drawable.ic_sports)
            .setPositiveButton("Igen"){ _, _ ->
                Toast.makeText(this,"hozzáadtad",Toast.LENGTH_SHORT).show()
            }.create()

        alert1.setOnClickListener {
            //addContactDialog.show()
            //singleChoiceDialog.show()
            multipleChoiceDialog.show()
        }

        buttonOpenActivity.setOnClickListener {
            val dataToSend = etName.text.toString()
            Intent(this,  SecondActivity::class.java).also {
                it.putExtra("EXTRA_DATA_TO_SEND", dataToSend)
                startActivity(it)
            }
        }

        //implicit intent
        openGallery.setOnClickListener {
            Intent( Intent.ACTION_GET_CONTENT ).also {
                it.type = "image/*"
                startActivityForResult(it, 0)
            }
        }

        /*
            data class Valami(
                val valami: String
            ): Serializable

            it.putExtra("valamiObj", new Valami("valami értéke"))
         */

    //val btnApply = findViewById<Button>(R.id.buttonApply)

        /*buttonApply.setOnClickListener {
            val stringToView = "${etFirstName.text} ${etLastName.text} ${etBirthDate.text} ${etCountry.text}"
            //Toast.makeText(this, stringToView, Toast.LENGTH_SHORT).show()
            tvRandom.text = stringToView
        }*/

        /*kép megjelenítése
        btnAddImage.setOnClickListener {
            ivImage.setImageResource(R.drawable.imageName)
        }

        radiobtn hivatkozás
        radioGroupId.checkedRadioButtonId

        checkBox.isChecked

        if stringben -> "valami" + (if(cond) "akk ez a szöveg" else "akk ez")

        customToast
         */

    /*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.Settings5 -> finish()
        }
        return true
    }

    //implicit intent visszatérési értéke
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == 0) {
            val uri = data?.data
            println(uri)
        }
    }

    //
    private fun check(){
        ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    override fun onPause() {
        super.onPause()
        println("on")
    }

    override fun onRestart() {
        super.onRestart()
    }
    */

    /*
     var todoList = mutableListOf(
            Todo("Follow AndroidDevs", true),
            Todo("Follow AndroidDevs2", false),
            Todo("Follow AndroidDevs3", true),
            Todo("Follow AndroidDevs4", true),
            Todo("Follow AndroidDevs5", true),
            Todo("Follow AndroidDevs6", false),
            Todo("Follow AndroidDevs7", true),
            Todo("Follow AndroidDevs8", true),
            Todo("Follow AndroidDevs9", false),
            Todo("Follow AndroidDevs10", true)
        )

        val adapter = TodoAdaptor(todoList)
        rvTodos.adapter = adapter
        rvTodos.layoutManager = LinearLayoutManager(this)

        btnAddTodo.setOnClickListener {
            val title = etTodo.text.toString()
            val todo = Todo(title, false)
            todoList.add(todo)
            adapter.notifyItemInserted(todoList.size-1)
        }
    * */

}

/* to do adaptor
* package com.example.fitnessappclient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdaptor(
    var todos: List<Todo>
) : RecyclerView.Adapter<TodoAdaptor.TodoViewHolder>(){

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.itemView.apply{
            tvTitle.text = todos[position].title
            cbDone.isChecked = todos[position].isChecked
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }

}
* oszály
* package com.example.fitnessappclient

data class Todo (
    val title: String,
    val isChecked: Boolean
    )

*
*
*
* fragment
package com.example.fitnessappclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FirstFragment : Fragment(R.layout.fragment_first) {

   /* override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
}
}*/

/*
2. activity
package com.example.fitnessappclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val name = intent.getStringExtra("EXTRA_DATA_TO_SEND")
        val anyInt = intent.getIntExtra("nemletezik", 0)
        //val myObject = intent.getSerializableExtra("extra neve") as CLASS
        tvSecondActivity.text = "$name, $anyInt"


        btnSecondActivity.setOnClickListener {
            finish()
        }
    }
}

fragment main
        val firstFragment = FirstFragment()
        val secondFragment = SecondFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFramgent, firstFragment)
            commit()
        }

        btnFragment1.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFramgent, firstFragment)
                addToBackStack(null) //visszaléptetéshez, lehet nevet is adni neki
                commit()
            }
        }

        btnFragment2.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFramgent, secondFragment)
                addToBackStack(null)
                commit()
            }
        }


 */


