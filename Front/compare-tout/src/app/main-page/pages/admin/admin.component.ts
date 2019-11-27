import {Component, OnInit} from '@angular/core';
import {SelectionModel} from '@angular/cdk/collections';
import {MatTableDataSource} from '@angular/material/table';
import {AdminService} from '../../../shared/services/admin.service';
import {Supplier} from '../../../shared/models/supplier.interface';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  displayedColumns: string[] = ['select', 'firstName', 'lastName', 'webSite', 'siret'];
  dataSource = null;
  selection = new SelectionModel<Supplier>(true, []);

  constructor(private adminService: AdminService) { }

  ngOnInit() {
    this.adminService.findAllSupplierWhoNeedValidate().subscribe(supplier => {
      this.dataSource = new MatTableDataSource<Supplier>(supplier);
    });
  }

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.data.forEach(row => this.selection.select(row));
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: Supplier): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id}`;
  }

  validerFourn() {
    this.selection.selected.forEach((supplier) => {
      this.adminService.validateSupplier(supplier).subscribe();
    });
    console.log('tout valide');
  }
}
